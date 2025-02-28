import { ethers } from "https://cdn.ethers.io/lib/ethers-5.2.esm.min.js";
import contractABI from "../static/contractABI.json"

const contractAddress = "0x7dcbFfde9cd4945B26e583B8785F8bB7B76e5922"

let provider;
let signer;
let contract;

async function connectWallet() {
    if(window.ethereum) {
        provider = new ethers.providers.Web3Provider(window.ethereum);
        await provider.send("eth_requestAccounts", []);
        signer = provider.getSigner();
        contract = new ethers.Contract(contractAddress, contractABI, signer);
        console.log("Contract connected:", contract);
    } else {
        alert("Please Install metamask!!");
    }
}

async function logOrder() {
    if(!contract) return alert("Connect Wallet first!!");
}