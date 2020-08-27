import React, {useState} from "react";

import {Link} from "react-router-dom";

export default function Deployments({projectId, tagName}){

    const pathName = (projectId) => `/projects/${projectId}/deployments`;

    return (
        <div>
            <Link className="btn btn-outline-info btn-lg btn-block mb-2" to={{
                pathname: pathName(projectId),
                state: {
                    projectId: projectId,
                    tagName: tagName
                }
            }}>

                <span className="fa fa-caret-right p-2"></span>
                <br/>Deployments
            </Link>
        </div>
    );

}


