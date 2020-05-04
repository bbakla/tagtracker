import React, {Component} from "react";
import {AddProject} from "./AddProject";
import {DisplayProjects} from "./DisplayProjects";
import Navigation from "./components/Navbar";
import {NavLink, Route, Router, Switch} from "react-router-dom";
import history from "./history";
import {DisplayTags} from "./tags/DisplayTags";

export default class ProjectDashboard extends Component {
    constructor(props) {
        super(props);

        this.state = {
            projects: [
                {
                    projectName: "TicketService",
                    projectId: "1",
                    description: "TicketService description",
                    tags: [
                        {
                            tagName: 'v0.1.0',
                            message: "message1",
                            releaseNotes: "releaseNote1"
                        },
                        {
                            tagName: 'v0.1.4',
                            message: "message2",
                            releaseNotes: "releaseNote2"
                        },
                        {
                            tagName: 'v0.2.0',
                            message: "message3",
                            releaseNotes: "releaseNote3"
                        }],
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
                    deployments: [
                        {
                            dev: "true",
                            int: "true",
                            prod: "false"
                        }
                    ]
                },
                {
                    projectName: "SorisAdapter",
                    projectId: "2",
                    description: "SorisAdapter description",
                    tags: [
                        {
                            tagName: 'v0.1.55',
                            message: "message51",
                            releaseNotes: "releaseNote51"
                        },
                        {
                            tagName: 'v0.1.45',
                            message: "message52",
                            releaseNotes: "releaseNote52"
                        },
                        {
                            tagName: 'v0.2.50',
                            message: "message53",
                            releaseNotes: "releaseNote53"
                        }],
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
                    deployments: [
                        {
                            dev: "true",
                            int: "true",
                            prod: "false"
                        }
                    ]
                }
            ]
        }
    }

    render = () =>

        <div className="container">

            <div className="row mt-3">
                <AddProject/>
            </div>
            <div className="row mt-5">
                <DisplayProjects projects={this.state.projects}/>
            </div>

        </div>
}
