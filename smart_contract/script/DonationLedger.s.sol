// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.13;

import {Script, console} from "forge-std/Script.sol";
import {DonationLedger} from "../src/DonationLedger.sol";

contract DonationLedgerScript is Script {
    DonationLedger public donationLedger;

    function setUp() public {}

    function run() public {
        uint privateKey = vm.envUint("PRIVATE_KEY");
        address account = vm.addr(privateKey);

        vm.startBroadcast(privateKey);

        donationLedger = new DonationLedger();

        vm.stopBroadcast();
    }
}
