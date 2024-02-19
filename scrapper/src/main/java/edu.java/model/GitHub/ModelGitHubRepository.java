package edu.java.model.GitHub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelGitHubRepository {


    private Long id ;
    private String name;

    public ModelGitHubRepository(Long id , String name){
        this.id = id ;
        this.name = name;
    }


}
