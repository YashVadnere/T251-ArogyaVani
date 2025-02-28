// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.13;

import "forge-std/Test.sol";
import "../src/DonationLedger.sol";

contract DonationLedgerTest is Test {
    DonationLedger public ledger;
    address donor1 = address(0x1);
    address donor2 = address(0x2);

    function setUp() public {
        ledger = new DonationLedger();
    }

    function testInitialState() view public {
        assertEq(ledger.orderCounter(), 0);
    }

    function testLogOrders() public {
        string memory donorName = "John Doe";
        string memory institution = "Red Cross";
        string memory storeName = "Walmart";
        string memory paymentMethod = "Credit Card";
        string[] memory itemNames = new string[](2);
        itemNames[0] = "Blankets";
        itemNames[1] = "Water Bottles";
        string[] memory quantities = new string[](2);
        quantities[0] = "10";
        quantities[1] = "50";
        string memory paymentAmount = "500.00";
        
        // Log an order as donor1
        vm.prank(donor1);
        ledger.logOrders(
            donorName,
            institution,
            storeName,
            paymentMethod,
            itemNames,
            quantities,
            paymentAmount
        );
        
        assertEq(ledger.orderCounter(), 1);
        
        assertEq(ledger.donorNames(donor1), donorName);
    }

    function testGetOrderBasicDetails() public {
        _setupTestOrder();
        
        (
            address returnedAddress,
            string memory returnedDonorName,
            uint256 returnedOrderId,
            string memory returnedInstitution,
            string memory returnedStore,
            ,
            ,
            ,
            ,
            string memory returnedStatus,
            uint256 returnedTimestamp
        ) = ledger.getOrder(1);
        
        assertEq(returnedAddress, donor2);
        assertEq(returnedDonorName, "Jane Smith");
        assertEq(returnedOrderId, 1);
        assertEq(returnedInstitution, "Local Shelter");
        assertEq(returnedStore, "Target");
        assertEq(returnedStatus, "Logged");
        assertTrue(returnedTimestamp > 0);
    }
    
    function testGetOrderItems() public {
        _setupTestOrder();
        
        (
            ,
            ,
            ,
            ,
            ,
            string[] memory returnedItemNames,
            string[] memory returnedQuantities,
            ,
            ,
            ,
            
        ) = ledger.getOrder(1);
        
        assertEq(returnedItemNames.length, 3);
        assertEq(returnedItemNames[0], "Canned Food");
        assertEq(returnedItemNames[1], "First Aid Kits");
        assertEq(returnedItemNames[2], "Hygiene Products");
        assertEq(returnedQuantities[0], "100");
        assertEq(returnedQuantities[1], "20");
        assertEq(returnedQuantities[2], "50");
    }
    
    function testGetOrderPaymentDetails() public {
        _setupTestOrder();
        
        (
            ,
            ,
            ,
            ,
            ,
            ,
            ,
            string memory returnedPaymentAmount,
            string memory returnedPaymentMethod,
            ,
            
        ) = ledger.getOrder(1);
        
        assertEq(returnedPaymentAmount, "750.50");
        assertEq(returnedPaymentMethod, "PayPal");
    }
    
    // Helper function to set up test order
    function _setupTestOrder() internal {
        string memory donorName = "Jane Smith";
        string memory institution = "Local Shelter";
        string memory storeName = "Target";
        string memory paymentMethod = "PayPal";
        string[] memory itemNames = new string[](3);
        itemNames[0] = "Canned Food";
        itemNames[1] = "First Aid Kits";
        itemNames[2] = "Hygiene Products";
        string[] memory quantities = new string[](3);
        quantities[0] = "100";
        quantities[1] = "20";
        quantities[2] = "50";
        string memory paymentAmount = "750.50";
        
        vm.prank(donor2);
        ledger.logOrders(
            donorName,
            institution,
            storeName,
            paymentMethod,
            itemNames,
            quantities,
            paymentAmount
        );
    }

    function testGetDonorName() public {
        string memory donorName = "Alex Johnson";
        
        vm.prank(donor1);
        ledger.logOrders(
            donorName,
            "Hospital",
            "Amazon",
            "Bitcoin",
            new string[](1),
            new string[](1),
            "100.00"
        );
        
        string memory returnedName = ledger.getDonorName(donor1);
        assertEq(returnedName, donorName);
    }

    function testFailItemQuantityMismatch() public {
        string[] memory itemNames = new string[](2);
        itemNames[0] = "Test Item 1";
        itemNames[1] = "Test Item 2";
        
        string[] memory quantities = new string[](1);
        quantities[0] = "1";
        
        vm.prank(donor1);
        ledger.logOrders(
            "Test Donor",
            "Test Institution",
            "Test Store",
            "Test Payment Method",
            itemNames,
            quantities,
            "100.00"
        );
    }

    function testFailOrderDoesNotExist() public view {
        ledger.getOrder(999);
    }

    function testMultipleOrders() public {
        // Log first order
        vm.prank(donor1);
        string[] memory items1 = new string[](1);
        items1[0] = "Item 1";
        string[] memory quantities1 = new string[](1);
        quantities1[0] = "1";
        
        ledger.logOrders(
            "Donor 1",
            "Institution 1",
            "Store 1",
            "Card",
            items1,
            quantities1,
            "100.00"
        );
        
        // Log second order
        vm.prank(donor2);
        string[] memory items2 = new string[](1);
        items2[0] = "Item 2";
        string[] memory quantities2 = new string[](1);
        quantities2[0] = "2";
        
        ledger.logOrders(
            "Donor 2",
            "Institution 2",
            "Store 2",
            "Cash",
            items2,
            quantities2,
            "200.00"
        );
        
        assertEq(ledger.orderCounter(), 2);
    }

    function testLogOrdersEvent() public {
        string memory donorName = "Event Tester";
        string memory institution = "Charity";
        string memory storeName = "eBay";
        string memory paymentMethod = "Ethereum";
        string[] memory itemNames = new string[](1);
        itemNames[0] = "Donation";
        string[] memory quantities = new string[](1);
        quantities[0] = "1";
        string memory paymentAmount = "1000.00";
        
        vm.recordLogs();
        
        vm.prank(donor1);
        ledger.logOrders(
            donorName,
            institution,
            storeName,
            paymentMethod,
            itemNames,
            quantities,
            paymentAmount
        );
        
        Vm.Log[] memory entries = vm.getRecordedLogs();
        
        assertEq(entries.length, 1);
        
        bytes32 eventSignature = keccak256("OrderLogged(uint256,address,string,string,string[],string[],string,string,string,uint256)");
        assertEq(entries[0].topics[0], eventSignature);
        
        assertEq(entries[0].topics[1], bytes32(uint256(1)));
    }
}