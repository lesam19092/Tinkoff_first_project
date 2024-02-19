package edu.java.model.GitHub;

import java.util.List;

public class ModelGitHubRepositoryList {

    private List<ModelGitHubRepository> repositories;

    public ModelGitHubRepositoryList() {
    }

    public ModelGitHubRepositoryList(List<ModelGitHubRepository> repositories) {
        this.repositories = repositories;
    }

    public List<ModelGitHubRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<ModelGitHubRepository> repositories) {
        this.repositories = repositories;
    }
}
