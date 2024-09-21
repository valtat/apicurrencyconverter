# Step 1: Build Stage
FROM node:lts-alpine AS build

# Step 2: Set Working Directory
WORKDIR /app

# Step 3: Copy Files
COPY package.json package-lock.json ./

# Step 4: Install Dependencies
RUN npm install

# Copy the rest of your app's source code from your host to your image filesystem.
COPY . .

# Step 5: Build the App
RUN npm run build

# Ensure build output
RUN ls -al /app/dist

# Step 6: Prune unnecessary files
RUN npm prune --production


# Step 7: Serve Stage
FROM node:lts-alpine

# Step 8: Set Working Directory
WORKDIR /app

# Step 9: Copy build artifacts from the build stage
COPY --from=build /app/dist ./dist
COPY --from=build /app/node_modules ./node_modules

# Step 10: Install `serve` to run the application
RUN npm install -g serve

# Step 11: Serve the App
CMD ["serve", "-s", "dist", "-l", "5137"]

# Expose port 5137 to the outside once the container has launched
EXPOSE 5137

