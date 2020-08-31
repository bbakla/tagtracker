import React, {useContext, useState} from "react";

import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {useLocation} from "react-router";
import {GlobalContext} from "../Store";
import Stage from "./Stage";

export default function DeploymentView(){

    let location = useLocation();
    const {projects} = useContext(GlobalContext)

    const getStages = () => {
        const project = projects.find(
            project => project.projectId === location.state.projectId);

        const stages = project.tags.find(tag => tag.tagName === location.state.tagName).stages

        return stages;
    }

    const [stages, setStages] = useState(() => getStages());

    const displayStages = () => {

        if (!Object.hasOwnProperty(stages)) {
            const a = Object.keys(stages).map((key, value) =>

                (<div key={key} className="col-sm">
                    <ul className="stage-column job-list">
                        <li>
                            <div className="stage-name">{key}</div>

                            <Stage jobs={stages[key]}
                                   stageName={key}
                                   projectId={location.state.projectId}
                                   tagName={location.state.tagName}
                            />
                        </li>
                    </ul>

                </div>)
            );

            return a;
        }
        }


    return (

        <div className="container">
        <div className="row mt-5">
                {displayStages()}
            </div>
        </div>


    );

}


