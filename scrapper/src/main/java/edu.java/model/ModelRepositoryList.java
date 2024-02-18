package edu.java.model;

import java.util.List;

public class ModelRepositoryList {

    private List<ModelRepository> repositories;

    public ModelRepositoryList() {
    }

    public ModelRepositoryList(List<ModelRepository> repositories) {
        this.repositories = repositories;
    }

    public List<ModelRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<ModelRepository> repositories) {
        this.repositories = repositories;
    }
}
