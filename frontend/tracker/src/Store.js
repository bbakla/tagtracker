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
                projectName: "TicketService",
                projectId: "1",
                description: "TicketService description",
                tags: [
                    {
                        tagName: 'v0.1.0',
                        message: "message1",
                        releaseNotes: "releaseNote1",
                        createdDate: "2018-01-01T23:28:56.782Z",
                        tagsDependentOnMe: [
                            {
                                projectName: "soris",
                                tag: "v0.1.0"
                            },
                            {
                                projectName: "rule-engine",
                                tag: "v0.1.1"
                            }],
                        tagsDependentOn: [
                            {
                                projectName: "dependentOn1",
                                tag: "v0.1.1"
                            }],
                        deployments:

                            {
                                "dev": "false",
                                "int": "false",
                                "prod": "false"
                            }


                    },
                    {
                        tagName: 'v0.1.4',
                        message: "message2",
                        releaseNotes: "releaseNote2",
                        createdDate: "2019-01-01T23:28:56.782Z",
                        tagsDependentOnMe: [
                            {
                                projectName: "soris",
                                tag: "v0.0.5555"
                            },
                            {
                                projectName: "rule-engine",
                                tag: "v0.1.0001"
                            }],
                        tagsDependentOn: [
                            {
                                projectName: "dependentOn1",
                                tag: "v0.00.111"
                            }],
                        deployments:
                            {
                                dev: "true",
                                int: "true",
                                prod: "false"
                            }

                    },
                    {
                        tagName: 'v0.2.0',
                        message: "message3",
                        releaseNotes: "releaseNote3",
                        createdDate: "2020-01-01T23:28:56.782Z",
                        tagsDependentOnMe: [
                            {
                                projectName: "soris",
                                tag: "v0.1.06767"
                            },
                            {
                                projectName: "rule-engine",
                                tag: "v0.1.879879781"
                            }],
                        tagsDependentOn: [
                            {
                                projectName: "dependentOn1",
                                tag: "v0.1.991"
                            }],
                        deployments:
                            {
                                "dev": "true",
                                "int": "false",
                                "prod": "false"
                            }
                    }]
            },
            {
                projectName: "SorisAdapter",
                projectId: "2",
                description: "SorisAdapter description",
                tags: [
                    {
                        tagName: 'v0.1.57775',
                        message: "message51",
                        releaseNotes: "releaseNote51",
                        createdDate: "2020-01-01T23:28:56.782Z",
                        tagsDependentOnMe: [
                            {
                                projectName: "soris5",
                                tag: "v0.1.580"
                            },
                            {
                                projectName: "rule-engine5",
                                tag: "v0.1.1445"
                            }],
                        tagsDependentOn: [
                            {
                                projectName: "dependentOn15",
                                tag: "v0.1.1445"
                            }],
                        deployments:
                            {
                                dev: "true",
                                int: "false",
                                prod: "false"
                            }
                    },
                    {
                        tagName: 'v0.1.45',
                        message: "message52",
                        releaseNotes: "releaseNote52",
                        createdDate: "2019-02-01T23:28:56.782Z",
                        tagsDependentOnMe: [
                            {
                                projectName: "soris565",
                                tag: "v0.1.50"
                            },
                            {
                                projectName: "rule-engine5",
                                tag: "v0.1.151111"
                            }],
                        tagsDependentOn: [
                            {
                                projectName: "dependentOn15",
                                tag: "v0.1.15"
                            }],
                        deployments:
                            {
                                dev: "true",
                                int: "false",
                                prod: "false"
                            }
                    },
                    {
                        tagName: 'v0.2.50',
                        message: "message53",
                        releaseNotes: "releaseNote53",
                        createdDate: "2020-03-01T23:28:56.782Z",
                        tagsDependentOnMe: [
                            {
                                projectName: "soris5",
                                tag: "v0.1.50"
                            },
                            {
                                projectName: "rule-engine5",
                                tag: "v0.1.15"
                            }],
                        tagsDependentOn: [
                            {
                                projectName: "dependentOn15",
                                tag: "v0.1.15"
                            }],
                        deployments:
                            {
                                dev: "true",
                                int: "true",
                                prod: "false"
                            }
                    }]
            }
        ]
    }*/

export const GlobalContext = createContext([]);

const Store = ({children}) => {
  const [{projects, isLoading, isError}, setProjects] = useInit();

  console.log(projects)

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






