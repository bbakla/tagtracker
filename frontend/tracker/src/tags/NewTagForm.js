import React, {Component, useState} from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button"

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
              onClick={this.open}
          >
            Create New Tag
          </Button>

          <Modal show={this.state.showModal} onHide={this.close}>
            <Modal.Header closeButton>
              <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <h4>Text in a modal</h4>
              <p>Duis mollis, est non commodo luctus, nisi erat porttitor
                ligula.</p>

              <h4>Popover in a modal</h4>
              <hr/>

            </Modal.Body>
            <Modal.Footer>
              <Button onClick={this.close}>Close</Button>
            </Modal.Footer>
          </Modal>
        </div>
    );
  }

}
