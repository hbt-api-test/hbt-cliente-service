package com.hbt.cliente.api.controller;

import com.hbt.cliente.api.model.entity.Cliente;
import com.hbt.cliente.api.model.entity.Region;
import com.hbt.cliente.api.model.entity.ResponseData;
import com.hbt.cliente.api.service.IClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ClienteController {

    private static final int MAX_ROW_PAGE = 4;

    @Inject
    IClienteService service;

    @Inject
    Validator validator;

    private Cliente cliente;
    private HttpStatus httpStatus;
    private int count = 0;
    private ResponseData<Object> responseData;
    private String message = "";




    @GetMapping("/all")
    @RolesAllowed("user")
    public Response findAll(){
        List<Cliente> clientes = service.findAllClients();
        httpStatus = HttpStatus.OK;
        message = "Datos encontrados";
        count = clientes.size();

        return Response.status(httpStatus.value())
                .entity(responseData(message,
                        count,
                        httpStatus,
                        clientes)).build();
    }

    @GetMapping("/regiones")
    public Response findAllRegiones(){
        List<Region> regiones = service.findAllRegiones();
        httpStatus = HttpStatus.OK;
        message = "Datos encontrados";
        count = regiones.size();

        return Response.status(httpStatus.value())
                .entity(responseData(message,
                        count,
                        httpStatus,
                        regiones)).build();
    }

    @GetMapping("/page/{page}/row/{row}")
    public Response findAll(@PathVariable Integer page, @PathVariable Integer row){
        Pageable pageable = PageRequest.of(page, row);
        Page<Cliente> clientes = service.findAllClients(pageable);
        httpStatus = HttpStatus.OK;
        message = "Datos encontrados";
        count = clientes.getNumberOfElements();

        return Response.status(httpStatus.value())
                .entity(responseData(message,
                        count,
                        httpStatus,
                        clientes)).build();
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable Long id) {

        try {
            cliente = service.findClientId(id);
            httpStatus = HttpStatus.OK;
            message = "Datos encontrados";
            count = 1;
        }catch (Exception e){
            message = e.getLocalizedMessage();
            log.error(message);
            httpStatus = HttpStatus.NOT_FOUND;
            count=0;
            responseData = responseData(message,
                    count,
                    httpStatus,
                    cliente);
            return Response.status(httpStatus.value()).entity(responseData).build();
        }
        return Response.status(httpStatus.value())
                .entity(responseData(message,
                        count,
                        httpStatus,
                        cliente)).build();
    }

    @PostMapping("/create")
    public Response create( @RequestBody Cliente cliente){

        Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);

        if(!constraintViolations.isEmpty()){
           List<String> errores = constraintViolations.stream()
                    .map(err -> "El campo '" + err.getPropertyPath().toString() + "' " + err.getMessage())
                   .collect(Collectors.toList());

            message = "Error de violaciòn de campos";
            httpStatus = HttpStatus.BAD_REQUEST;
            count=0;
            responseData = responseData(message,
                    count,
                    httpStatus,
                    errores);
            return Response.status(httpStatus.value()).entity(responseData).build();

        }


        try{
           this.cliente =  service.create(cliente);
           httpStatus = HttpStatus.OK;
           count = 1;
           message = "El cliente ha sido creado con exito";
        }catch (Exception e){
            message = e.getLocalizedMessage();
            log.error(message);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            count=0;
            responseData = responseData(message,
                    count,
                    httpStatus,
                    this.cliente);
            return Response.status(httpStatus.value()).entity(responseData).build();
        }
        return Response.status(httpStatus.value())
                .entity(responseData(message,
                        count,
                        httpStatus,
                        this.cliente)).build();
    }

    @PutMapping("/edit/{id}")
    public Response edit(@RequestBody Cliente cliente, @PathVariable Long id) {

        Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);

        if(!constraintViolations.isEmpty()){
            List<String> errores = constraintViolations.stream()
                    .map(err -> "El campo '" + err.getPropertyPath().toString() + "' " + err.getMessage())
                    .collect(Collectors.toList());

            message = "Error de violaciòn de campos";
            httpStatus = HttpStatus.BAD_REQUEST;
            count=0;
            responseData = responseData(message,
                    count,
                    httpStatus,
                    errores);
            return Response.status(httpStatus.value()).entity(responseData).build();

        }

        try{
            this.cliente =  service.edit(cliente, id);
            httpStatus = HttpStatus.OK;
            count = 1;
            message = "El cliente ha sido actualizado con exito";
        }catch (Exception e){
            message = e.getLocalizedMessage();
            log.error(message);
            count=0;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseData = responseData(message,
                    count,
                    httpStatus,
                    this.cliente);
            return Response.status(httpStatus.value()).entity(responseData).build();
        }

        return Response.status(httpStatus.value())
                .entity(responseData(message,
                        count,
                        httpStatus,
                        this.cliente)).build();
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id){
        try {
            service.delete(id);
            httpStatus = HttpStatus.OK;
            count = 1;
            message = "El cliente ha sido eliminado con exito";
        }catch (Exception e){
            message = e.getLocalizedMessage();
            log.error(message);
            count=0;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseData = responseData(message,
                    count,
                    httpStatus,
                    this.cliente);
            return Response.status(httpStatus.value()).entity(responseData).build();
        }

        return Response.status(httpStatus.value())
                .entity(responseData(message,
                        count,
                        httpStatus,
                        this.cliente)).build();
    }



    private ResponseData<Object> responseData(String message, int count, HttpStatus httpStatus, Object T){
        return ResponseData.builder()
                .message(message)
                .count(count)
                .status(httpStatus)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .payload(T)
                .build();
    }

}
