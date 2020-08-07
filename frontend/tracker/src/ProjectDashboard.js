import React from "react";
import AddProject from "./AddProject";
import DisplayProjects from "./DisplayProjects";


export default function ProjectDashboard() {

  /*

    const deleteProject = (projectName) => {
        setProjects(projects.filter(project => project.projectName !== projectName))
    }

    const test = (a) => {
        console.log(a)
    }*/

    /* function createTag (projectId, newTag) {
         const createNewTag = async () => {
           let path = tagBasePath.replace("{identifier}",
               projectId)

             const body = JSON.stringify(newTag)

             console.log(body)

           /!*  axios.post(path,
                 body,
                 {
                     headers: {
                         'Content-Type': 'application/json',
                     }
                 }
             ).then(response => console.log(response.data));*!/


         };

         createNewTag();
     }*/

    return (
        <div className="container">

                <div className="row mt-3">

                    <AddProject/>
                </div>
                <div className="row mt-5">


                    <DisplayProjects />

                </div>


        </div>
    );
}

