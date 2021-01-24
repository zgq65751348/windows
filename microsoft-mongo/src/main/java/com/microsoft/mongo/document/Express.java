package com.microsoft.mongo.document;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.persistence.Id;
import java.io.Serializable;


@Document
@Data
public class Express implements Serializable {

    private final  static  long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed
    @Field("name")
    private String name;


    @Field("telephone")
    private String telephone;

}
