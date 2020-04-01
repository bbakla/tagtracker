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
                    <tr>
                        <td>Soris Adapter</td>
                        <td>v2.1.1</td>

                    </tr>
                    <tr>
                        <td>Rule Engine</td>
                        <td>v1.0.0</td>

                    </tr>
                    </tbody>
                </Table>
            </div>
        );
    }

}
