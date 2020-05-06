import React, {Component} from 'react';

import './App.css';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';

import {Route, Switch} from "react-router-dom";
import {DisplayTags} from "./tags/DisplayTags";
import ProjectDashboard from "./ProjectDashboard";
import Navbar from "./components/Navbar";
import {RelatedProjects} from "./dependencies/RelatedProjects";

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



    render = () =>
           // <Router history={history}>
        <div>
        <div>
             {/*   <Link to="/"  exact="true">
                    <span className="img-thumbnail"> <i className="fa fa-home"></i>Home</span>
               </Link>*/}
               <Navbar/>
              </div>
                <Switch>
                    {/*<Route exact path="/projects" render={ (props) => <ProjectDashboard {...props} projects = {this.state.projects}/>}/>*/}
                    <Route exact path="/projects" component={ProjectDashboard}/>
                    <Route exact path="/projects/tags" component={DisplayTags}/>
                    <Route exact path="/projects/dependentOn" component={RelatedProjects}/>
                    <Route exact path="/projects/dependentToMe" component={RelatedProjects}/>
                    }

                </Switch>
    </div>
//            </Router>


}

