package edu.java.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelRepository {


    private Long id ;
    private String name;

    public ModelRepository(Long id , String name){
        this.id = id ;
        this.name = name;
    }


}
