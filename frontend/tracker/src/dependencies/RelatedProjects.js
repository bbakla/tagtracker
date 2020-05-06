import React, {Component} from "react";
import {Table} from "react-bootstrap";
import {DependencyModal} from "./DependencyModal";
import {TagFormModal} from "../tags/TagFormModal";

export class RelatedProjects extends Component {

    constructor(props) {
        super(props);

        this.state = {
            showModal: true
        }
    }

    close = () => {
        this.setState({showModal: false});
    }

    open = () => {
        this.setState({showModal: true});
    }

    deleteDependency = () => {

    }

    render() {
        const dependencies = this.props.location.state.dependencies.map(dependency => {

            return (
                <tr key={dependency.projectName + "_" + dependency.tag}>
                    <td>{dependency.projectName}</td>
                    <td>{dependency.tag}</td>

                    <td>
                        <div>

                            <DependencyModal buttonLabel = "Edit"
                                             projectName = {dependency.projectName}
                                             tagName = {dependency.tag}
                            >

                            </DependencyModal>
                            <button type="button" className="btn btn-sm btn-danger"
                                    onClick={() => this.deleteDependency(dependency.projectName)}>
                                <i className=" far fa-trash-alt"></i>
                            </button>
                        </div>
                    </td>

                </tr>

            )
        });

        return (
            <div className="container">
                <h3 className="mt-3">{this.props.location.state.dependencyType}</h3>
                <div className="row">
                    <div className="col-12">
                        <Table responsive hover>
                            <thead>
                            <tr>
                                <th>Project Name</th>
                                <th>Tag</th>
                            </tr>
                            </thead>
                            <tbody>
                            {dependencies}
                            </tbody>
                        </Table>

                        <DependencyModal
                            buttonLabel="Create"
                            saveDependency = {this.saveDependency}
                        />
                    </div>
                </div>
            </div>
        );
    }

}
