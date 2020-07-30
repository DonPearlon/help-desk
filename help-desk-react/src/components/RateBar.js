import React, { Component } from "react";

class RateBar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rate: this.props.rate
    };
  }

  render() {
    const RateBarRender = () => {
      const rate = this.state.rate;
      if (rate === "1" || rate === 1) {
        return <ProgressRate1 />;
      }
      if (rate === "2" || rate === 2) {
        return <ProgressRate2 />;
      }
      if (rate === "3" || rate === 3) {
        return <ProgressRate3 />;
      }
      if (rate === "4" || rate === 4) {
        return <ProgressRate4 />;
      }
      if (rate === "5" || rate === 5) {
        return <ProgressRate5 />;
      }
      return <></>;
    };
    return (
      <>
        <RateBarRender />
      </>
    );
  }
}
export default RateBar;

const ProgressRate1 = () => (
  <div className="progress">
    <div
      className="progress-bar bg-danger"
      role="progressbar"
      style={{
        width: "20%"
      }}
      aria-valuenow="20"
      aria-valuemin="0"
      aria-valuemax="100"
    ></div>
  </div>
);

const ProgressRate2 = () => (
  <div className="progress">
    <div
      className="progress-bar bg-warning"
      role="progressbar"
      style={{
        width: "40%"
      }}
      aria-valuenow="40"
      aria-valuemin="0"
      aria-valuemax="100"
    ></div>
  </div>
);

const ProgressRate3 = () => (
  <div className="progress">
    <div
      className="progress-bar bg-info"
      role="progressbar"
      style={{
        width: "60%"
      }}
      aria-valuenow="60"
      aria-valuemin="0"
      aria-valuemax="100"
    ></div>
  </div>
);

const ProgressRate4 = () => (
  <div className="progress">
    <div
      className="progress-bar "
      role="progressbar"
      style={{
        width: "80%"
      }}
      aria-valuenow="80"
      aria-valuemin="0"
      aria-valuemax="100"
    ></div>
  </div>
);

const ProgressRate5 = () => (
  <div className="progress">
    <div
      className="progress-bar bg-success"
      role="progressbar"
      style={{
        width: "100%"
      }}
      aria-valuenow="100"
      aria-valuemin="0"
      aria-valuemax="100"
    ></div>
  </div>
);
