import React, {Component} from 'react';

import './App.css';

import ProjectDashboard from "./ProjectDashboard";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';

import Navigation from './components/Navbar';
import history from "./history";
import {Route, Router, Switch} from "react-router-dom";
import {DisplayTags} from "./tags/DisplayTags";

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

<div>
  <Router history={history}>
    <Switch>
      <Route exact path="/" component={ProjectDashboard}/>
      <Route exact path="/tags" component={DisplayTags}/>
    </Switch>
  </Router>
</div>

}

