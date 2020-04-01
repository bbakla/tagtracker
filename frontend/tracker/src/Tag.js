import React, {Component} from "react";
import history from './history';
import {Button} from "react-bootstrap";

export class Tag extends Component {
  constructor(props) {
    super(props);

    this.state = {
      showTagOptions: false,
    }
  }

/*  handleOptions = () => {
   if (this.state.showTagOptions) {
      this.setState({showTagOptions: false});

    } else {
      this.setState({showTagOptions: true});

    }

  }*/

  render() {

      return(
          <div>
              <Button className="btn btn-outline-success btn-lg btn-block mb-2"
                      role="button" onClick={() => history.push('/tags')} >
        <span>
            <span className="fa fa-tag p-2"></span>
            <span className="badge badge-light">v0.1.1</span>
        </span>
                  <br/>Tags
              </Button>
          </div>
      );
  }

     /* <div>
        <button className="btn btn-outline-success btn-lg btn-block mb-2"
                role="button" onClick={this.handleOptions}>
        <span>
            <span className="fa fa-tag p-2"></span>
            <span className="badge badge-light">v0.1.1</span>
        </span>
          <br/>Tags
        </button>
        {this.state.showTagOptions && <TagOptions/>}

      </div>*/

}
