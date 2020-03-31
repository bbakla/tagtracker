import React, {Component} from "react";

export class DependentToMe extends Component {
  render = () =>
      <a href="#" className="btn btn-outline-primary btn-lg btn-block mb-2"
         role="button">
        <span className="fa fa-caret-left p-2"></span>
        <br/>DependentToMe</a>
}
