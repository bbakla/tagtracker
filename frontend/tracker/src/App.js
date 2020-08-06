import React from 'react';

import './App.css';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';

import {Route, Switch} from "react-router-dom";
import DisplayTags from "./tags/DisplayTags";
import ProjectDashboard from "./ProjectDashboard";
import Navbar from "./navigation/Navbar";
import RelatedProjects from "./dependencies/RelatedProjects";

export default function App() {

    return (

        <div>
            <div>
                <Navbar/>
            </div>
            <Switch>
                {/*<Route exact path="/projects" render={ (props) => <ProjectDashboard {...props} projects = {this.state.projects}/>}/>*/}
                <Route exact path="/projects" component={ProjectDashboard}/>
                <Route exact path="/projects/:projectId/tags" component={DisplayTags}/>
                <Route exact path="/projects/:projectId/dependentOn" component={RelatedProjects}/>
                <Route exact path="/projects/:projectId/dependentToMe" component={RelatedProjects}/>
                }

            </Switch>
        </div>
//            </Router>


    )
}

