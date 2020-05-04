import React, {Component} from "react";
import {Table} from "react-bootstrap";

export class RelatedProjects extends Component{

    render() {
        return (
            <div>
                <Table responsive>
                    <thead>
                    <tr>
                        <th>Project Name</th>
                        <th>Tag</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.props.dependencies.map(dependency => (
                        <tr key={dependency.projectName + "_" + dependency.tag}>
                            <td>{dependency.projectName}</td>
                            <td>{dependency.tag}</td>

                        </tr>
                    ))}

                   {/* <tr>
                        <td>Soris Adapter</td>
                        <td>v2.1.1</td>

                    </tr>
                    <tr>
                        <td>Rule Engine</td>
                        <td>v1.0.0</td>

                    </tr>*/}
                    </tbody>
                </Table>
            </div>
        );
    }

}
