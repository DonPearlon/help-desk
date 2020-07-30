import React, { Component } from "react";
import axios from "axios";

class LogIn extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: undefined,
      email: "",
      password: "",
      errorMessage: "",
      token: ""
    };
  }

  enterShop = () => {
    this.props.history.push("/help-desk");
  };

  handleChangeEmail = e => {
    this.setState({
      email: e.target.value
    });
  };
  handleChangePassword = e => {
    this.setState({
      password: e.target.value
    });
  };

  handleAuth = () => {
    axios
      .post("http://localhost:8080/help-desk/api/users/sign-in", {
        email: this.state.email,
        password: this.state.password
      })
      .then(response => {
        this.setState(
          { user: response.data, token: response.headers["authorization"] },
          () => {
            console.log("User Authenticated : " + this.state.user.email);
            this.props.handleLogIn(this.state.user, this.state.token);
            this.props.history.push("/help-desk");
          }
        );
      })
      .catch(error => {
        let errorStr = error.response.data.errors[0];
        if (
          this.isBlank(this.state.email) ||
          this.isBlank(this.state.password)
        ) {
          errorStr = "Please fill out the required field.";
        }
        this.setState(
          {
            validCredentials: false,
            errorMessage: errorStr
          },
          () => {
            console.log("errorMessage : " + this.state.errorMessage);
          }
        );
      });
  };

  isBlank = str => {
    return !str || /^\s*$/.test(str);
  };
  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-10 mx-auto col-md-8 mt-4">
            <h3 className="text-capitalize text-center">Log In to Help Desk</h3>
            <div className="card card-body my-3">
              <form>
                <div className="input-group">
                  <div className="input-group-prepend">
                    <div className="input-group-text bg-primary text-white">
                      <i className="fas fa-book" />
                    </div>
                  </div>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter Email"
                    onChange={this.handleChangeEmail}
                  ></input>
                </div>
                <div className="input-group">
                  <div className="input-group-prepend">
                    <div className="input-group-text bg-primary text-white">
                      <i className="fas fa-book" />
                    </div>
                  </div>
                  <input
                    type="password"
                    className="form-control"
                    placeholder="Enter Password"
                    onChange={this.handleChangePassword}
                  ></input>
                </div>
                <h6 className="text-capitalize text-center">
                  {this.state.errorMessage}
                </h6>
                <button
                  type="button"
                  className="btn btn-block btn-primary mt-3"
                  onClick={this.handleAuth}
                >
                  Enter
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default LogIn;
