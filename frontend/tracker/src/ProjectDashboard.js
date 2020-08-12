import React, {useContext, useEffect} from "react";
import AddProject from "./AddProject";
import DisplayProjects from "./DisplayProjects";
import {GlobalContext} from "./Store";
import axios from "axios";
import {basePathForProjects} from "./paths";

export default function ProjectDashboard() {

  const {isLoading, loading, projects, setProjects} = useContext(GlobalContext);

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

/*  useEffect(() => {
    const fetchData = async () => {

      try {
        const result = await axios(basePathForProjects);
        setProjects(result.data);

      } catch (e) {
        console.log(e)
      }

    };

    fetchData();
  }, []);*/

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

