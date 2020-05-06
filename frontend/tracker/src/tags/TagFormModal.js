import React, {Component} from "react";
import Button from "react-bootstrap/Button"
import {Modal} from "react-bootstrap";

export class TagFormModal extends Component {

  constructor(props) {
    super(props);

    this.state = {
      showModal: false,
      tagName: this.props.tagName,
      message: this.props.message,
      releaseNotes: this.props.releaseNotes
    }
  }

  close = () => {
    this.setState({showModal: false});
  }

  open = () => {
    this.setState({showModal: true});
  }

  saveTag = () => {
this.props.saveTag({
    name: this.state.tagName,
    message: this.state.message,
    releaseNotes: this.state.releaseNotes
    })
    this.close()
  }

  saveValue = (event) => {
    this.setState({[event.target.name] : event.target.value});
  }

  render() {
    const label = this.props.buttonLabel;
    let title = "";
    let button = '';
    let disabled = false;

        if(label === "Edit") {
          title = "Edit tag";
          disabled= true;
          button = <button className="float-left btn btn-sm btn-success" onClick={this.open}>
                      <i className="fas fa-edit"></i>
                  </button>
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
                  <input type="text" className="form-control" name="tagName" aria-describedby="tagName"  disabled = {disabled}  onChange={this.saveValue}
                         placeholder="Enter tag name with the format v2.5.6" defaultValue={this.state.tagName}/>
                    <small id="emailHelp" className="form-text text-muted">should be in the format of v0.2.1 </small>
                </div>
                <div className="form-group">
                  <label htmlFor="messageInput">Message</label>
                  <input type="text" className="form-control" name="message" onChange={this.saveValue} placeholder="Enter message" disabled = {disabled} defaultValue={this.state.message}/>
                </div>

                <div className="form-group">
                  <label htmlFor="releaseNotesInput">Release note</label>
                  <input type="text" className="form-control" name="releaseNotes" onChange={this.saveValue} placeholder="Enter release notes" defaultValue={this.state.releaseNotes}/>
                </div>
              </form>

            </Modal.Body>
            <Modal.Footer>
              <button type="submit" className="btn btn-primary" onClick={this.saveTag}>{label}</button>
              <Button onClick={this.close}>Close</Button>
            </Modal.Footer>
          </Modal>
        </div>
    );
  }

}
