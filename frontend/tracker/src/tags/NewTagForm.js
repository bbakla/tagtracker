import React, {Component, useState} from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button"
import {DeployEnvironment} from "../deployment/DeployEnvironment";
import {TagFormModal} from "./TagFormModal";

export class NewTagForm extends Component {

  constructor(props) {
    super(props);

    this.state = {
      showModal: false,
    }
  }

  close = () => {
    this.setState({showModal: false});
  }

  open = () => {
    this.setState({showModal: true});
  }

  render() {

    return (
        <div>
          <Button
              className="btn btn-outline-primary btn-block"
              onClick={this.open}>
            Create New Tag
          </Button>

          {this.state.showModal && <TagFormModal/>}

        </div>
    );
  }

}
