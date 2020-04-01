import React, {Component} from "react";
import {NewTagForm} from "./NewTagForm";
import {DisplayTags} from "./DisplayTags";

export class TagOptions extends Component {
  render() {
    return (
        <div className="btn-group-sm pb-4">
        <DisplayTags/>
          <button className="btn btn-outline-danger btn-block">Delete Tag
          </button>
          <NewTagForm/>
        </div>
    );
  }
}
