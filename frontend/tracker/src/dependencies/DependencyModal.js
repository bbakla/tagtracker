import React, {Component} from "react";
import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";

export class DependencyModal extends Component {

    constructor(props) {
        super(props);

        this.state = {
            showModal: false,
            projectName: this.props.projectName,
            tagName: this.props.tagName
        }
    }

    open = () => {
        this.setState({showModal: true});
    }

    close = () => {
        this.setState({showModal: false});
    }

    saveDependency = () => {
        this.props.saveDependency({
            projectName: this.state.projectName,
            tagName: this.state.tagName
        });

        this.close();
    }

    saveValue = (event) => {
        this.setState({[event.target.name]: event.target.value});
    }

    render() {
        let label = this.props.buttonLabel;
        let button = '';

        if (label === "Edit") {
            label = "Save"
            button = <button className="float-left btn btn-sm btn-success" onClick={this.open}>
                <i className="fas fa-edit"></i>
            </button>
        } else {
            label = "Create"
            button =
                <Button className="btn btn-outline-primary btn-block" onClick={this.open}>Create dependency</Button>
        }

        return (
            <div>
                {button}
                <Modal show={this.state.showModal}>
                    <Modal.Header>Dependency</Modal.Header>
                    <Modal.Body>
                        <form>
                            <div className="form-group">
                                <label htmlFor="projectNameInput">Project Name</label>
                                <input type="text" className="form-control" name="projectName"
                                       defaultValue={this.state.projectName} onChange={this.saveValue}/>
                            </div>

                            <div className="form-group">
                                <label htmlFor="tagNameInput">Tag Name</label>
                                <input type="text" className="form-control" name="tagName"
                                       defaultValue={this.state.tagName} onChange={this.saveValue}/>
                            </div>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <button type="submit" className="btn btn-primary" onClick={this.saveDependency}>{label}</button>
                        <Button onClick={this.close}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }

}
