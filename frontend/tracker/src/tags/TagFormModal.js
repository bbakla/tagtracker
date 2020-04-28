import React, {Component, useState} from "react";
import Button from "react-bootstrap/Button"
import {Modal} from "react-bootstrap";

export class TagFormModal extends Component {

  constructor(props) {
    super(props);

    this.state = {
      showModal: false
    }
  }

  close = () => {
    this.setState({showModal: false});
  }

  open = () => {
    this.setState({showModal: true});
  }


  render() {
    const label = this.props.buttonLabel;
    let title = ""
    let button = ''

        if(label === "Edit") {
          title = "Edit tag";
          button = <Button type="button" variant={"success"} onClick={this.open}
                           style={{float: "left"}}>
                      <i className="fas fa-edit"></i>
                  </Button>
        } else {
          button =  <Button className="btn btn-outline-primary btn-block" onClick={this.open}>
            Create New Tag
          </Button>
          title = "Create New Tag";
        }


    return (
        <div>
          {button}
          <Modal show={this.state.showModal}>
            <Modal.Header>{title}</Modal.Header>
            <Modal.Body>

              <form>
                <div className="form-group">
                  <label htmlFor="tagNameInput">Tag Name </label>
                  <input type="text" className="form-control" id="tagNameInput" aria-describedby="tagName"
                         placeholder="Enter tag name with the format v2.5.6"/>
                    <small id="emailHelp" className="form-text text-muted">should be in the format of v0.2.1 </small>
                </div>
                <div className="form-group">
                  <label htmlFor="messageInput">Message</label>
                  <input type="text" className="form-control" id="messageInput" placeholder="Enter message"/>
                </div>

                <div className="form-group">
                  <label htmlFor="releaseNotesInput">Message</label>
                  <input type="text" className="form-control" id="releaseNotesInput" placeholder="Enter release notes"/>
                </div>
              </form>

            </Modal.Body>
            <Modal.Footer>
              <Button type="submit" className="btn btn-primary" onClick={this.toggle}>{label}</Button>
              <Button onClick={this.close}>Close</Button>
            </Modal.Footer>
          </Modal>
        </div>
    );
  }

}
