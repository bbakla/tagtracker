import React from "react";
import {Link} from "react-router-dom";

import {DEPENDENT_ON} from "./dependency";

export default function ShowDependency({relationshipType, projectName, projectId, tagName}) {

  const buttonName = relationshipType === DEPENDENT_ON ? "Dependent on"
      : "Related with";
  const title = relationshipType === DEPENDENT_ON ? projectName
      + "dependencies " : projectName + " relations "
  const pathName = (projectId) => `/projects/${projectId}/${relationshipType}`;

  return (
      <div>

        <Link className="btn btn-outline-info btn-lg btn-block mb-2" to={{
          pathname: pathName(projectId),
          state: {
            // dependencies: dependencies,
            dependencyTitle: title,
            projectName: projectName,
            projectId: projectId,
            relationshipType: relationshipType,
            tagName: tagName
          }
            }}>

                <span className="fa fa-caret-right p-2"></span>
                <br/>{buttonName}
            </Link>
        </div>);


}
