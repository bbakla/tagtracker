import React, {Component} from "react";
import {AddProject} from "./AddProject";
import {DisplayProjects} from "./DisplayProjects";
import Navigation from "./components/Navbar";
import {NavLink, Route, Router, Switch} from "react-router-dom";
import history from "./history";
import {DisplayTags} from "./tags/DisplayTags";

export default class ProjectDashboard extends Component {
    render = () =>

        <div className="container">

            <div className="row mt-3">
                <AddProject/>
            </div>
            <div className="row mt-5">
                <DisplayProjects projects={this.props.projects}/>
            </div>


        </div>
}
