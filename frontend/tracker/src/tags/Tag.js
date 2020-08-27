import React, {useContext} from "react";
import {Link} from "react-router-dom";

export default function Tag({currentTagName, projectName, projectId, tags}) {
  const pathName = (projectId) => `/projects/${projectId}/tags`;

    return (
        <div>
            <Link className="btn btn-outline-success btn-lg btn-block mb-2" to={{
                pathname: pathName(projectId),
                state: {
                    tags: tags,
                    projectName: projectName,
                    projectId: projectId
                }
            }}>
         <span>
            <span className="fa fa-tag p-2"></span>
            <span className="badge badge-light">{currentTagName}</span>
        </span>
                <br/>Tags
            </Link>
        </div>

    );

}
