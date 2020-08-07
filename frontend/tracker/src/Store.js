import axios from "axios";
import {basePathForProjects} from "./paths";
import React, {createContext, useEffect, useReducer, useState} from "react";
import AppReducer from "./AppReducer";

   const initialState = {
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
      }


export const GlobalContext = createContext([])

const Store = ({children}) => {
    const [{projects, isLoading, isError}, setProjects] = useInit();

    function addRepository(projectIdentifier) {
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

    return (
        <GlobalContext.Provider value={{
            projects,
            addRepository

        }}>

                {children}

        </GlobalContext.Provider>
    );
};


const useInit = () => {

    const [projects, setProjects] = useState([]);

    const [isLoading, setLoading] = useState(false);
    const [isError, setError] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            setError(false);
            setLoading(true);

            try {
                const result = await axios(basePathForProjects);
                setProjects(result.data);
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






