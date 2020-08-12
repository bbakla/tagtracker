import React, {useContext, useEffect, useState} from "react";
import {Table} from "react-bootstrap";
import DependencyModal from "./DependencyModal";
import {useLocation} from "react-router";
import {DEPENDENT_ON, DEPENDENT_ON_ME} from "./dependency";

import {GlobalContext} from "../Store";

export default function RelatedProjects() {

  const location = useLocation();
  const {saveDependency, deleteDependency, projects} = useContext(
      GlobalContext);
  const [dependencies, setDependencies] = useState( () => {

          const tag = projects.find(
              p => p.projectName === location.state.projectName).tags.find(
              t => t.tagName === location.state.tagName);

         if (location.state.relationshipType === DEPENDENT_ON_ME) {
             return tag.tagsDependentOnMe
         } else {
           return tag.tagsDependentOn;
         }
  })

  const handleDeleteDependency = (projectName) => {
    let confirmDelete = window.confirm("Are you sure deleting the dependency?")
    if (confirmDelete) {

      setDependencies(dependencies.filter(d => d.projectName !== projectName));
    }
  }

  const handleSaveDependency = (dependency) => {
    const newDependency = {
      projectName: dependency.projectName,
      tagName: dependency.tagName,
      projectId: dependency.projectId
    }

      saveDependency(location.state.projectId, location.state.tagName,
          newDependency, location.state.relationshipType)

    setDependencies(depends => [...depends, newDependency]);

}

const updateDependency = (updated) => {

    let newDependencies = dependencies.map(d => {
        if (d.projectName === updated.projectName) {

            return Object.assign({}, d,
                {
                    projectName: updated.projectName,
                    tag: updated.tagName
                });
        } else {
            return d;
        }
    });

    setDependencies(newDependencies);
}

    const tagDependencies = dependencies.map(dependency => {

        return (
            <tr key={dependency.projectName + "_" + dependency.tagName}>
              <td>{dependency.projectName}</td>
              <td>{dependency.tagName}</td>

              <td>
                  {  location.state.relationshipType === DEPENDENT_ON &&

                (<div>

                  <DependencyModal buttonLabel="Edit"
                                   pName={dependency.projectName}
                                   tName={dependency.tagName}
                                   saveDependency={updateDependency}
                                   projects={location.state.projects}
                  />

                  <button type="button" className="btn btn-sm btn-danger"
                          onClick={() => handleDeleteDependency(
                              dependency.projectName)}>
                    <i className=" far fa-trash-alt"></i>
                  </button>
                </div>)
                  }
              </td>


            </tr>

        )
    });

  return (
      <div className="container">
        <h3 className="mt-3">{location.state.dependencyTitle}</h3>
        <div className="row">
          <div className="col-12">
            <Table responsive hover>
              <thead>
              <tr>
                <th>Project Name</th>
                            <th>Tag</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tagDependencies}
                        </tbody>
                    </Table>

                    <DependencyModal
                        buttonLabel="Create"
                        saveDependency={handleSaveDependency}
                        pName={location.state.projectName}
                        projects={location.state.projects}
                        relationshipType={location.state.relationshipType}
                    />
                </div>
            </div>
        </div>
    );

}
