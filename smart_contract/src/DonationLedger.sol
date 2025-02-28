// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.13;

contract DonationLedger {
    struct Item {
        string itemName;
        string quantity;
    }

    mapping(address => string) public donorNames;

    struct Order {
        uint256 orderId;
        address donorAddress;
        string institution;
        string store;
        Item[] items;
        string paymentAmount;
        string paymentMethod;
        string status;
        uint256 timestamp;
    }

    uint256 public orderCounter;
    mapping(uint256 => Order) public orders;

    event OrderLogged(
        uint256 indexed orderId,
        address donorAddress,
        string institution,
        string store,
        string[] itemNames,
        string[] quantities,
        string paymentAmount,
        string paymentMethod,
        string status,
        uint256 timestamp
    );

    function logOrders(
        string memory _donorName,
        string memory _institution,
        string memory _storeName,
        string memory _paymentMethod,
        string[] memory _itemNames,
        string[] memory _quantities,
        string memory _paymentAmount
    ) public {
        require(
            _itemNames.length == _quantities.length,
            "Items and quantities count mismatch"
        );

        donorNames[msg.sender] = _donorName;

        orderCounter++;
        Order storage newOrder = orders[orderCounter];

        newOrder.donorAddress = msg.sender;
        newOrder.store = _storeName;
        newOrder.institution = _institution;
        newOrder.orderId = orderCounter;
        newOrder.paymentAmount = _paymentAmount;
        newOrder.paymentMethod = _paymentMethod;
        newOrder.status = "Logged";
        newOrder.timestamp = block.timestamp;

        for (uint256 i = 0; i < _itemNames.length; i++) {
            newOrder.items.push(
                Item({itemName: _itemNames[i], quantity: _quantities[i]})
            );
        }

        emit OrderLogged(
            newOrder.orderId,
            newOrder.donorAddress,
            newOrder.institution,
            newOrder.store,
            _itemNames,
            _quantities,
            newOrder.paymentAmount,
            newOrder.paymentMethod,
            newOrder.status,
            newOrder.timestamp
        );
    }

    function getOrder(
        uint256 _orderId
    )
        public
        view
        returns (
            address,
            string memory,
            uint256,
            string memory,
            string memory,
            string[] memory,
            string[] memory,
            string memory,
            string memory,
            string memory,
            uint256
        )
    {
        require(
            _orderId > 0 && _orderId <= orderCounter,
            "Order does not exist"
        );

        Order storage order = orders[_orderId];
        uint256 itemsCount = order.items.length;
        string[] memory itemNames = new string[](itemsCount);
        string[] memory quantities = new string[](itemsCount);

        for (uint256 i = 0; i < itemsCount; i++) {
            itemNames[i] = order.items[i].itemName;
            quantities[i] = order.items[i].quantity;
        }

        return (
            order.donorAddress,
            donorNames[order.donorAddress],
            order.orderId,
            order.institution,
            order.store,
            itemNames,
            quantities,
            order.paymentAmount,
            order.paymentMethod,
            order.status,
            order.timestamp
        );
    }

    function getDonorName(
        address _donorAddress
    ) public view returns (string memory) {
        return donorNames[_donorAddress];
    }
}
