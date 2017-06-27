module.exports = {
    entry : './js_src/index.js',

    output: {
        path: __dirname + '/src/main/web/resources/js/app',
        filename: 'bundle.js'
    },

    module: {
        loaders: [
            {
                test:/\.js$/,
                loader:'babel-loader',
                exclude:'/node_module',
                query: {
                    cacheDirectory:true,
                    presets:['es2015','react']
                }
            }
        ]
    }
};