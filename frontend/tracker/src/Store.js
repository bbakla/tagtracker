import axios from "axios";
import {
  dependentOnPath,
  basePathForProjects,

} from "./paths";
import React, {createContext, useEffect, useReducer, useState} from "react";
import AppReducer from "./AppReducer";
import {DEPENDENT_ON, DEPENDENT_ON_ME} from "./dependencies/dependency";

 /*const initialState = {
        projects: [
            {
    "createdBy": "user",
    "createdDate": "2020-08-19T06:39:56.717+0000",
    "lastModifiedBy": "user",
    "lastModifiedDate": "2020-08-19T06:39:56.717+0000",
    "projectId": "135330",
    "projectName": "GitlabCI",
    "encodedPath": "baris.bakla1%252Fgitlabci",
    "description": "",
    "tags": [
        {
            "projectId": "135330",
            "projectName": "GitlabCI",
            "message": "",
            "tagName": "v0.0.1",
            "releaseNotes": "* test",
            "tagsDependentOn": [],
            "tagsDependentOnMe": [],
            "stages": {
                "build-runner": {
                    "stage": "build-runner",
                    "stageIndex": 0,
                    "pipelineStatus": "success",
                    "pipelineId": "6552843",
                    "jobResources": [
                        {
                            "jobId": "30276993",
                            "status": "success",
                            "name": "build runner",
                            "stage": "build-runner",
                            "createdAt": null,
                            "startedAt": null,
                            "duration": null,
                            "userResource": {
                                "userId": 12176,
                                "name": "Baris Bakla",
                                "userName": "baris.bakla1"
                            },
                            "pipelineStatus": null,
                            "pipelineId": null
                        }
                    ]
                },
                "build": {
                    "stage": "build",
                    "stageIndex": 1,
                    "pipelineStatus": "success",
                    "pipelineId": "6552843",
                    "jobResources": [
                        {
                            "jobId": "30276997",
                            "status": "manual",
                            "name": "build dev",
                            "stage": "build",
                            "createdAt": null,
                            "startedAt": null,
                            "duration": null,
                            "userResource": {
                                "userId": 12176,
                                "name": "Baris Bakla",
                                "userName": "baris.bakla1"
                            },
                            "pipelineStatus": null,
                            "pipelineId": null
                        }
                    ]
                },
                "test": {
                    "stage": "test",
                    "stageIndex": 2,
                    "pipelineStatus": "success",
                    "pipelineId": "6552843",
                    "jobResources": [
                        {
                            "jobId": "30277001",
                            "status": "success",
                            "name": "test allgemein",
                            "stage": "test",
                            "createdAt": null,
                            "startedAt": null,
                            "duration": null,
                            "userResource": {
                                "userId": 12176,
                                "name": "Baris Bakla",
                                "userName": "baris.bakla1"
                            },
                            "pipelineStatus": null,
                            "pipelineId": null
                        }
                    ]
                },
                "qualitygate": {
                    "stage": "qualitygate",
                    "stageIndex": 3,
                    "pipelineStatus": "success",
                    "pipelineId": "6552843",
                    "jobResources": [
                        {
                            "jobId": "30277005",
                            "status": "manual",
                            "name": "check MyQuality",
                            "stage": "qualitygate",
                            "createdAt": null,
                            "startedAt": null,
                            "duration": null,
                            "userResource": {
                                "userId": 12176,
                                "name": "Baris Bakla",
                                "userName": "baris.bakla1"
                            },
                            "pipelineStatus": null,
                            "pipelineId": null
                        }
                    ]
                }
            }
        }
    ]
}
        ]
    }*/

export const GlobalContext = createContext([]);

const Store = ({children}) => {
    const [{projects, isLoading, isError}, setProjects] = useInit();

  const addRepository = (projectIdentifier) => {
    const body = {
      "identifier": projectIdentifier
    }

    const postProject = async () => {
      const response = await axios.post(basePathForProjects, body);

      const project = {
        projectName: response.data.projectName,
        projectId: response.data.projectId,
                description: response.data.description,
                tags: response.data.tags
      }

      setProjects(projectList => [...projectList, project]);

    };

    postProject();

  }

  const createTag = (projectId, newTag) => {
    const path = (identifier) => `/projects/${identifier}/tags`;
    const body = JSON.stringify(newTag)

    const createTagRequest = async () => {
      const response = await axios.post(path(projectId), body, {
        headers: {
          'Content-Type': 'application/json',
        }
      }).then(response => {

        let toBeUpdatedProject = projects.find(
            project => project.projectId === projectId)
        toBeUpdatedProject.tags.push(newTag);

        let otherProjects = projects.filter(
            project => project.projectId !== projectId)
        otherProjects.push(toBeUpdatedProject)

        setProjects(otherProjects)
      })
      .catch(err => console.log(err))
    }

    createTagRequest();

  };

  const deleteTag = (projectId, tagName) => {
    const path = (projectId, tagName,
        isRemoteTagDeleted) => `/projects/${projectId}/tags/${tagName}?deleteRemoteTag=${isRemoteTagDeleted}`

    const deleteTagRequest = async () => {
      const response = await axios.delete(path(projectId, tagName, "true"))
      .then(response => {
        let toBeUpdatedProject = projects.find(
            project => project.projectId === projectId)
        const tagsAfterDeletion = toBeUpdatedProject.tags.filter(
            tag => tag.tagName !== tagName)
        toBeUpdatedProject.tags = tagsAfterDeletion;

        let otherProjects = projects.filter(
            project => project.projectId !== projectId)
        otherProjects.push(toBeUpdatedProject)

        setProjects(otherProjects);

      })
      .catch(err => console.log(err))
    }

    deleteTagRequest();
  }

  const saveDependency = (projectId, tagName, newDependency,
      dependencyType) => {
    let path = dependentOnPath;

    if (dependencyType === DEPENDENT_ON) {
      path = path.replace("{identifier}", projectId)
      .replace("{tagName}", tagName)

    } else if (dependencyType === DEPENDENT_ON_ME) {
      path = path.replace("{identifier}", projectId)
      .replace("{tagName}", tagName);
    }

    const saveDependency = async () => {
      const response = await axios.put(path, JSON.stringify(newDependency), {
        headers: {
          'Content-Type': 'application/json',
          'X-operation': 'save'
        }
      }).catch(err => console.log(err))
    }

    saveDependency();
  }

  const deleteDependency = (projectId, projectTagId, dependencyToBeRemoved) => {

    let path = dependentOnPath.replace("{identifier}", projectId)
    .replace("{tagName}", projectTagId)

    const removeDependency = async () => {
      const response = await axios.put(path,
          JSON.stringify(dependencyToBeRemoved), {
            headers: {
              'Content-Type': 'application/json',
              'X-operation': 'remove'
            }
          }).catch(err => console.log(err))
    }

    removeDependency();
  }

  const loading = () => {
    return (<div>
      <h1>Loading</h1>
    </div>)
  }

  return (
      <GlobalContext.Provider value={{
        projects,
        setProjects,
        addRepository,
        createTag,
        deleteTag,
        isLoading,
        loading,
        saveDependency,
        deleteDependency,
      }}>
        {isLoading ? loading() : children}

        </GlobalContext.Provider>
    );
};


const useInit = () => {

    const [projects, setProjects] = useState([]);

    const [isLoading, setLoading] = useState(false);
    const [isError, setError] = useState(false);

    useEffect(() => {
        const fetchData = async () => {

            try {
              const result = await axios(basePathForProjects);
              setProjects(result.data);
              //  setProjects(initialState.projects)
              setError(false);
              setLoading(true);


            } catch (e) {
                setError(true);
            }

            setLoading(false);
        };

        fetchData();
    }, []);


    return [{projects, isLoading, isError}, setProjects]
}




/*

export const GlobalProvider = ({children}) => {
    const [state, dispatch] = useReducer(AppReducer, initialState, undefined);


    console.log(init())

    function test(a) {
        console.log(a);

    }
    const addRepository = (projectData) => {
        //console.log(projectData)

        const project = {
            projectName: projectData.projectName,
            projectId: projectData.projectId,
            description: projectData.description,
            tags: projectData.tags
        };

        //  setProjects(projectList => [...projectList, project])
    }

    return (
        <Store.Provider value={{
            projects: initialState.projects,
            test2,
            test,
            addRepository
        }}
        >
            {children}
        </Store.Provider>);
}
*/


export default Store;






