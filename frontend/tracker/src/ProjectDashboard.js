import React, {createContext, useEffect, useState} from "react";
import AddProject from "./AddProject";
import DisplayProjects from "./DisplayProjects";
import axios from 'axios';
import {basePathForProjects, createTagPath} from "./paths";

export default function ProjectDashboard() {
 /*   const initialState = {
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
                        dependentToMe: [
                            {
                                projectName: "soris",
                                tag: "v0.1.0"
                            },
                            {
                                projectName: "rule-engine",
                                tag: "v0.1.1"
                            }],
                        dependentOn: [
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
                        dependentToMe: [
                            {
                                projectName: "soris",
                                tag: "v0.0.5555"
                            },
                            {
                                projectName: "rule-engine",
                                tag: "v0.1.0001"
                            }],
                        dependentOn: [
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
                        dependentToMe: [
                            {
                                projectName: "soris",
                                tag: "v0.1.06767"
                            },
                            {
                                projectName: "rule-engine",
                                tag: "v0.1.879879781"
                            }],
                        dependentOn: [
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
                        dependentToMe: [
                            {
                                projectName: "soris5",
                                tag: "v0.1.580"
                            },
                            {
                                projectName: "rule-engine5",
                                tag: "v0.1.1445"
                            }],
                        dependentOn: [
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
                        dependentToMe: [
                            {
                                projectName: "soris565",
                                tag: "v0.1.50"
                            },
                            {
                                projectName: "rule-engine5",
                                tag: "v0.1.151111"
                            }],
                        dependentOn: [
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
                        dependentToMe: [
                            {
                                projectName: "soris5",
                                tag: "v0.1.50"
                            },
                            {
                                projectName: "rule-engine5",
                                tag: "v0.1.15"
                            }],
                        dependentOn: [
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


    const [projects, setProjects] = useState([]);


    const addRepository = (projectData) => {
        //console.log(projectData)

        const project = {
            projectName: projectData.projectName,
            projectId: projectData.projectId,
            description: projectData.description,
            tags: projectData.tags
        };

        setProjects(projectList => [...projectList, project])
    }

    const deleteProject = (projectName) => {
        setProjects(projects.filter(project => project.projectName !== projectName))
    }


    function createTag (projectId, newTag) {
        const createNewTag = async () => {
            let path = createTagPath.replace("{identifier}",
                projectId)

            const body = JSON.stringify(newTag)

            console.log(body)

          /*  axios.post(path,
                body,
                {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
            ).then(response => console.log(response.data));*/


        };

        createNewTag();
    }

    useEffect(()=> {

      const fetchData = async () => {

         /* const response = await fetch(basePathForProjects)
              .then(response => response.json())
              .then(data => console.log(data))*/

          const projects = await axios(basePathForProjects);
        setProjects(projects.data);
        console.log(projects.data)
        };

        fetchData();




    }, [])

    return (
        <div className="container">

            <div className="row mt-3">
                <AddProject addProject={addRepository}/>
            </div>
            <div className="row mt-5">
                <ProjectContext.Provider
                    value={{
                        projects,
                        createTag
                    }}>

                    <DisplayProjects
                        removeProjectFromTheList={deleteProject}
                    />

                </ProjectContext.Provider>

            </div>

        </div>
    );
}

export const ProjectContext = createContext({});

