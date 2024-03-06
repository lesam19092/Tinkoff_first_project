package edu.java.controller;

import edu.java.model.request.AddLinkRequest;
import edu.java.model.request.RemoveLinkRequest;
import edu.java.model.response.ApiErrorResponse;
import edu.java.model.response.LinkResponse;
import edu.java.model.response.ListLinksResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Validated
public interface LinksApi {

    @Operation(summary = "Убрать отслеживание ссылки", description = "", tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно убрана",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = LinkResponse.class))),

        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),

        @ApiResponse(responseCode = "404",
                     description = "Ссылка не найдена",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})
    @RequestMapping(value = "/links",
                    produces = {"application/json"},
                    consumes = {"application/json"},
                    method = RequestMethod.DELETE)
    ResponseEntity<LinkResponse> linksDelete(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        RemoveLinkRequest body
    );

    @Operation(summary = "Получить все отслеживаемые ссылки", description = "", tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ListLinksResponse.class))),

        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})
    @RequestMapping(value = "/links",
                    produces = {"application/json"},
                    method = RequestMethod.GET)
    ResponseEntity<ListLinksResponse> linksGet(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId
    );

    @Operation(summary = "Добавить отслеживание ссылки", description = "", tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно добавлена",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = LinkResponse.class))),

        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})
    @RequestMapping(value = "/links",
                    produces = {"application/json"},
                    consumes = {"application/json"},
                    method = RequestMethod.POST)
    ResponseEntity<LinkResponse> linksPost(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        AddLinkRequest body
    );

}
