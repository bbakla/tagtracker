import React, {Component, useContext} from "react";
import {Link} from "react-router-dom";
import {ProjectContext} from "../ProjectDashboard";
import {DEPENDENT_ON} from "./dependency";

export default function ShowDependency({relationshipType, projectName, projectId, dependencies}) {
    const {projects} = useContext(ProjectContext);

    const buttonName = relationshipType === DEPENDENT_ON ? "DependentOn" : "DependentToMe";
    const dependencyType = relationshipType === DEPENDENT_ON ? "Depends on " + projectName : "Projects dependent on " + projectName
    const pathName = (projectId) => `/projects/${projectId}/${relationshipType}`;

    return (
        <div>

            <Link className="btn btn-outline-info btn-lg btn-block mb-2" to={{
                pathname: pathName(projectId),
                state: {
                    dependencies: dependencies,
                    dependencyTitle: dependencyType,
                    projects: projects,
                    projectName: projectName,
                    relationshipType: relationshipType

                }
            }}>

                <span className="fa fa-caret-right p-2"></span>
                <br/>{buttonName}
            </Link>
        </div>);


}
