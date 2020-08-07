import React, {Component, useContext, useState} from "react";
import {Table} from "react-bootstrap";
import DependencyModal from "./DependencyModal";
import {useLocation} from "react-router";
import {DEPENDENT_ON, DEPENDENT_ON_ME} from "./dependency";
import {addDependentOn, addDependentOnMe} from "../paths";
import axios from 'axios';
import {ProjectContext} from "../ProjectDashboard";

export default function RelatedProjects() {

    const location = useLocation();
    const [dependencies, setDependencies] = useState(location.state.dependencies)
    const {testApi} = useContext(ProjectContext);

    const deleteDependency = (projectName) => {
        let confirmDelete = window.confirm("Are you sure deleting the tag?")
        if (confirmDelete) {

            setDependencies(dependencies.filter(d => d.projectName !== projectName));
        }
    }


const saveDependency = (dependency) => {
    const newDependency = {
        projectName: dependency.projectName,
        tag: dependency.tagName
    }

    debugger;
    testApi.a("e");

    setDependencies(dependencies => [...dependencies, newDependency]);


/*    if(location.state.relationshipType === DEPENDENT_ON) {
        let path = addDependentOn.replace("{identifier}", dependency.projectName)
            .replace("{tagName", dependency.tagName)

        axios.patch(path, )

    } else if (location.state.relationshipType === DEPENDENT_ON_ME) {
        let path = addDependentOnMe.replace("{identifier}", dependency.projectName)
            .replace("{tagName", dependency.tagName)
    }*/

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
            <tr key={dependency.projectName + "_" + dependency.tag}>
                <td>{dependency.projectName}</td>
                <td>{dependency.tag}</td>

                <td>
                    <div>

                        <DependencyModal buttonLabel="Edit"
                                         pName={dependency.projectName}
                                         tName={dependency.tag}
                                         saveDependency={updateDependency}
                                         projects ={location.state.projects}


                        >

                        </DependencyModal>
                        <button type="button" className="btn btn-sm btn-danger"
                                onClick={() => deleteDependency(dependency.projectName)}>
                            <i className=" far fa-trash-alt"></i>
                        </button>
                    </div>
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
                        saveDependency={saveDependency}
                        pName={location.state.projectName}
                        projects ={location.state.projects}
                    />
                </div>
            </div>
        </div>
    );

}
