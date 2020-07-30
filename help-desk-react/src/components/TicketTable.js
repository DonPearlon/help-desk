import React, { Component } from "react";
import ReactTable from "react-table-6";
import "react-table-6/react-table.css";
import { Link } from "react-router-dom";
import TicketAction from "./TicketAction";

class TicketTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: this.props.user,
      tickets: this.props.tickets
    };
  }

  render() {
    function capitalize(str) {
      return str.charAt(0).toUpperCase() + str.slice(1);
    }
    function columnItemOutput(item) {
      let outputStr = item.toLowerCase();
      outputStr = outputStr.charAt(0).toUpperCase() + outputStr.slice(1);
      return outputStr.split("_").join(" ");
    }

    function filterCaseInsensitive(filter, row) {
      const id = filter.pivotId || filter.id;
      const content = row[id];
      if (typeof content !== "undefined") {
        if (typeof content === "object" && content !== null && content.key) {
          return String(content.key)
            .toLowerCase()
            .includes(filter.value.toLowerCase());
        } else {
          return String(content)
            .toLowerCase()
            .includes(filter.value.toLowerCase());
        }
      }

      return true;
    }

    const { tickets } = this.props;
    const urgencyMap = new Map();

    urgencyMap.set("critical", 1);
    urgencyMap.set("high", 2);
    urgencyMap.set("average", 3);
    urgencyMap.set("low", 4);

    const columns = [
      {
        Header: "ID",
        accessor: "id",
        width: 60,
        style: { textAlign: "center" }
      },
      {
        Header: "Name",
        accessor: "name",
        Cell: props => {
          const toHelpDeskTicket = {
            pathname: "/help-desk/ticket/" + props.original.id
          };
          return (
            <Link to={toHelpDeskTicket}>{capitalize(props.original.name)}</Link>
          );
        }
      },
      {
        Header: "Desired date",
        accessor: "desiredResolutionDate"
      },
      {
        Header: "Urgency",
        accessor: "urgency",
        sortMethod: (a, b) => {
          return urgencyMap.get(a.toLowerCase()) >
            urgencyMap.get(b.toLowerCase())
            ? -1
            : 1;
        },
        Cell: props => {
          const urgency = columnItemOutput(props.original.urgency);
          return urgency;
        }
      },
      {
        Header: "State",
        accessor: "state",
        Cell: props => {
          const state = columnItemOutput(props.original.state);
          return state;
        }
      },
      {
        Header: "Action",
        style: { textAlign: "center" },
        width: 250,
        Cell: props => {
          return (
            <TicketAction
              userId={this.state.user.id}
              ticketId={props.original.id}
              performTicketAction={this.props.performTicketAction}
              handleFeedbackLeave={this.props.handleFeedbackLeave}
              handleFeedbackView={this.props.handleFeedbackView}
              getAuthorization={this.props.getAuthorization}
            />
          );
        },
        sortable: false,
        filterable: false
      }
    ];
    return (
      <div className="container mb-3 mt-3">
        <ReactTable
          columns={columns}
          data={tickets}
          filterable
          defaultFilterMethod={(filter, row) =>
            filterCaseInsensitive(filter, row)
          }
          defaultPageSize={5}
        ></ReactTable>
      </div>
    );
  }
}
export default TicketTable;
