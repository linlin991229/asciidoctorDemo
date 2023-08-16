package org.acme.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("file")
public class FileUploadResource {
    /**
     * 文本文档可以用String接收,图片之类的不行
     *
     * @param file     file
     * @param fileName fileName
     * @return fineName 文件名
     */
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String uploadFile(@FormParam("file") String file, @FormParam("fileName") String fileName) {
        System.out.println(fileName);
        return file;
    }
}
