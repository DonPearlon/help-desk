import React, { Component } from "react";
import axios from "axios";

class HistoryTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      history: [],
      buttonAll: true
    };
  }
  componentDidMount() {
    this.getHistory();
  }
  getHistory() {
    const userId = this.props.userId;
    const ticketId = this.props.ticketId;
    const authorization = this.props.getAuthorization();
    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/history",
        {
          headers: { authorization: authorization }
        }
      )
      .then(response => {
        this.setState({ history: response.data }, () => {
          console.log(this.state);
        });
      });
  }

  getFullHistory() {
    const userId = this.props.userId;
    const ticketId = this.props.ticketId;
    const authorization = this.props.getAuthorization();
    axios
      .get(
        "http://localhost:8080/help-desk/api/users/" +
          userId +
          "/tickets/" +
          ticketId +
          "/history/full",
        {
          headers: { authorization: authorization }
        }
      )
      .then(response => {
        this.setState({ history: response.data }, () => {
          console.log(this.state);
        });
      });
  }
  changeToFullHistory = () => {
    this.setState({ buttonAll: false });
    this.getFullHistory();
  };

  changeToTop5HistoryEvents = () => {
    this.setState({ buttonAll: true });
    this.getHistory();
  };

  render() {
    const HistoryRows = () => (
      <>
        {this.state.history.map((event, i) => (
          <tr key={++i}>
            <td>{event.date}</td>
            <td>{event.userName}</td>
            <td>{event.action}</td>
            <td>{event.description}</td>
          </tr>
        ))}
      </>
    );
    return (
      <div className="container mb-3 mt-3">
        {this.state.buttonAll ? (
          <button
            type="button"
            className="btn btn-outline-primary btn-sm"
            onClick={this.changeToFullHistory}
          >
            Show All
          </button>
        ) : (
          <button
            type="button"
            className="btn btn-outline-primary btn-sm"
            onClick={this.changeToTop5HistoryEvents}
          >
            Show Top 5
          </button>
        )}
        <table
          className="table table-striped table-bordered"
          style={{ width: "100%" }}
        >
          <thead>
            <tr>
              <th>Date</th>
              <th>User</th>
              <th>Action</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            <HistoryRows />
          </tbody>
        </table>
      </div>
    );
  }
}
export default HistoryTable;
