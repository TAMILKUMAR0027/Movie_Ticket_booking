# Ticket Booking System - Render Deployment

## Free Deployment to Render.com

### Steps:

1. **Push this project to GitHub**
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin https://github.com/TAMILKUMAR0027/Movie_Ticket_booking.git
   git push -u origin main
   ```

2. **Deploy on Render.com**
   - Go to https://render.com
   - Sign up/Login with GitHub
   - Click **"New +"** → **"Web Service"**
   - Connect your GitHub repo
   - Configure:
     - **Name:** `ticket-booking-system`
     - **Region:** Singapore (closest to you)
     - **Branch:** `main`
     - **Root Directory:** (leave empty)
     - **Runtime:** `Node`
     - **Build Command:** `mvn clean package -DskipTests`
     - **Start Command:** `java -jar target/TicketBookingJavaProject.war`
     - **Plan:** `Free`

3. **Wait for deployment** (~2-3 minutes)

4. **Access your app** at: `https://ticket-booking-system.onrender.com`

---

## Alternative: Deploy Frontend Only (Netlify - Easiest)

1. Go to https://netlify.com
2. Drag and drop the `src/main/webapp` folder
3. Done! Free hosting in seconds

---

## Build WAR locally:
```bash
mvn clean package
```

## Run locally:
```bash
mvn jetty:run
```
