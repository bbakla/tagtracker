import React, {Component} from 'react';

import './App.css';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';

import Navigation from './components/Navbar';

import {Link, Route, Router, Switch} from "react-router-dom";
import {DisplayTags} from "./tags/DisplayTags";
import ProjectDashboard from "./ProjectDashboard";

export default class App extends Component {
   /* state = {
        groups: [],
        applications: [{
            projectId: "", encodedPath: "", tag: {},
            applicationName: "", dependentToMe: [], dependentTo: []
        }]
    };*/

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

   /* constructor(props) {
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
    }*/

    render = () =>
           // <Router history={history}>
        <div>
        <div>
                <Link to="/"  exact="true">Home</Link>
              </div>
                <Switch>
                    {/*<Route exact path="/projects" render={ (props) => <ProjectDashboard {...props} projects = {this.state.projects}/>}/>*/}
                    <Route exact path="/projects" component={ProjectDashboard}/>
                    <Route exact path="/projects/tags" component={DisplayTags}/>}
                </Switch>
    </div>
//            </Router>


}

