import React, {Component, useState} from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button"

export class TagFormModal extends Component {

  constructor(props) {
    super(props);

    this.state = {
      showModal: true,
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
          <Modal show={this.state.showModal} onHide={this.close}>
            <Modal.Header closeButton>
              <Modal.Title>New Tag</Modal.Title>
            </Modal.Header>
            <Modal.Body>

              <form>
                <div className="form-group">
                  <label htmlFor="tagNameInput">Tag Name </label>
                  <input type="text" className="form-control" id="tagNameInput" aria-describedby="emailHelp"
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
              <button type="submit" className="btn btn-primary">Submit</button>
              <Button onClick={this.close}>Close</Button>
            </Modal.Footer>
          </Modal>
    );
  }

}
