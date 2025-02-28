from fastapi import APIRouter, Request
from src.config import templates

router = APIRouter(
    prefix="/auth"
)

@router.get("/institute")
async def institute_auth(request: Request):
    return templates.TemplateResponse("institutelogin.html", {"request": request})

@router.get("/donor")
async def donor_auth(request: Request):
    return templates.TemplateResponse("loginuser.html", {"request": request})

@router.get("/vendor")
async def vendor_auth(request: Request):
    return templates.TemplateResponse("vendorlogin.html", {"request": request})