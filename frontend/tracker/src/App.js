import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import {AddProject} from "./AddProject";
import ProjectDashboard from "./ProjectDashboard";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';

export default class App extends Component {
  state = {
    groups: [],
    applications: [{
      projectId: "", encodedPath: "", tag: {},
      applicationName: "", dependentToMe: [], dependentTo: []
    }]
  };

  /*    async componentDidMount() {
          const response = await fetch('/api/example');
          const body = await response.json();
          this.setState({groups: body});
      }

      listExamples = () =>
          this.state.groups.map(example =>
              <div key={example.id}>
                  {example.name}
              </div>
          );

      addApplication = (application) => {
        console.log("this part will display the application")
      }*/

  render = () =>

      <div className="container">
        <ProjectDashboard/>
      </div>
}

