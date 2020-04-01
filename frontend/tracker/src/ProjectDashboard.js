import React, {Component} from "react";
import {AddProject} from "./AddProject";
import {DisplayProjects} from "./DisplayProjects";
import Navigation from "./components/Navbar";



export default class ProjectDashboard extends Component {

  render = () =>
      <div className="container">

        <div className="row mt-3">

          <AddProject/>
        </div>
        <div className="row mt-5">

          <DisplayProjects/>

        </div>

      </div>

}
