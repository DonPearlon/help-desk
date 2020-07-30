import React, { Component } from "react";
import axios from "axios";
import TicketTable from "./TicketTable";

class HelpDesk extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: this.props.user,
      password: this.props.password,
      tickets: []
    };
  }

  componentDidMount() {
    this.getTickets();
  }

  getTickets = () => {
    const id = this.state.user.id;
    const authorization = this.props.getAuthorization();
    axios
      .get("http://localhost:8080/help-desk/api/users/" + id + "/tickets/all", {
        headers: { authorization: authorization }
      })
      .then(response => {
        this.setState({ tickets: response.data }, () => {
          console.log(this.state);
        });
      });
  };

  getMyTickets = () => {
    const id = this.state.user.id;
    const authorization = this.props.getAuthorization();
    axios
      .get("http://localhost:8080/help-desk/api/users/" + id + "/tickets/my", {
        headers: { authorization: authorization }
      })
      .then(response => {
        this.setState({ tickets: response.data }, () => {
          console.log(this.state);
        });
      });
  };

  logOutHelpDesk = () => {
    this.props.history.push("/logout");
  };
  handleFeedbackLeave = id => {
    this.props.history.push("/help-desk/ticket/" + id + "/feedback/leave");
  };
  handleFeedbackView = id => {
    this.props.history.push("/help-desk/ticket/" + id + "/feedback/view");
  };
  toTicketCreation = () => {
    this.props.history.push("/help-desk/create-ticket");
  };

  performTicketAction = (ticketId, actionId) => {
    const userId = this.state.user.id;
    const authorization = this.props.getAuthorization();
    axios
      .put(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/actions/" +
          actionId,
        {},
        {
          headers: { authorization: authorization }
        }
      )
      .then(() => {
        this.getTickets();
      })
      .catch(error => console.log(error));
  };

  render() {
    return (
      <div
        className="container"
        style={{
          marginTop: "2%"
        }}
      >
        <h1 className="text-capitalize text-center">Help Desk</h1>

        <div className="d-flex justify-content-between my-2">
          <button
            type="button"
            className="btn btn-danger btn-sm"
            onClick={this.logOutHelpDesk}
          >
            Logout
          </button>
        </div>
        <h5 className="text-left">
          User: {this.state.user.firstName} {this.state.user.lastName}
        </h5>

        {this.state.user.role.toLowerCase() === "employee" ||
        this.state.user.role.toLowerCase() === "manager" ? (
          <>
            <div className="d-flex justify-content-between">
              <div></div>
              <div>
                <button
                  className="btn btn-block btn-success mt-3"
                  onClick={this.toTicketCreation}
                >
                  Create New Ticket
                </button>
              </div>
            </div>
          </>
        ) : (
          <div> </div>
        )}

        {this.state.user.role.toLowerCase() !== "employee" ? (
          <>
            <div className="d-flex justify-content-between my-2">
              <button
                className="btn btn-block btn-primary mt-3"
                onClick={this.getTickets}
              >
                All Tickets
              </button>
              &nbsp;
              <button
                className="btn btn-block btn-outline-info mt-3"
                onClick={this.getMyTickets}
              >
                My Tickets
              </button>
            </div>
          </>
        ) : (
          <h2 className="text-capitalize text-center">My Tickets</h2>
        )}
        <TicketTable
          user={this.state.user}
          tickets={this.state.tickets}
          performTicketAction={this.performTicketAction}
          handleFeedbackLeave={this.handleFeedbackLeave}
          handleFeedbackView={this.handleFeedbackView}
          getAuthorization={this.props.getAuthorization}
        />
      </div>
    );
  }
}
export default HelpDesk;
