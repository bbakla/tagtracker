import React from 'react';
import './Navbar.css';
import { Navbar } from 'react-bootstrap';
import {Link, withRouter} from 'react-router-dom';

const Navigation = () => {
    return (
        <Navbar bg="primary-outline" variant="dark">
            <Navbar.Brand href=""> <Link to="/projects" exact="true">
                <span className="img-thumbnail"> <i className="fa fa-home"></i>Home</span>
            </Link></Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
            </Navbar.Collapse>
        </Navbar>
    )
}

export default withRouter(Navigation);
